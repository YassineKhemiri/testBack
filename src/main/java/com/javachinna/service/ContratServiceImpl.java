package com.javachinna.service;

import com.javachinna.model.Contrat;
import com.javachinna.model.CountType;
import com.javachinna.model.Payment;
import com.javachinna.repo.ContratRepo;
import com.javachinna.repo.PaymentRepo;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ContratServiceImpl implements ContratService {
    @Autowired
    private StripeService stripeService;

    @Autowired
    private ContratRepo contratRepo;

    @Autowired
    private PaymentRepo paymentRepo;

    @Override
    public List<Contrat> getAllContarts() {
        return contratRepo.findAll();
    }

    @Override
    public List<Contrat> findContratByUserId(Long id) {
        return contratRepo.findContratByUserId(id);
    }

    @Override
    public Contrat getContartById(Long id) {
        Optional<Contrat> o = contratRepo.findById(id);
        return o.isPresent() ? o.get() : null;
    }

    @Override
    public void deleteContart(Long id) {
        contratRepo.deleteById(id);
    }

    @Override
    public List<CountType> getPercentageGroupByBranche() {
        return contratRepo.getPercentageGroupByBranche();
    }

    @Override
    public String createPaymentSession(Long contratId) throws Exception {
        try {
            Contrat contrat = getContartById(contratId);
            if (contrat == null) {
                throw new Exception("Contrat not found");
            }
            Long amount = contrat.getPrix();
            if (amount == null) {
                throw new Exception("Amount not specified for the contract");
            }
            String contractName = contrat.getNumContrat();
            Session session = stripeService.createSession(amount, contractName, contrat.getUser().getId(), contrat);
            return session.getId();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to create payment session: " + e.getMessage(), e);
        }
    }

    @Override
    public void updatePaymentStatus(String sessionId, boolean success) throws Exception {
        Payment payment = paymentRepo.findBySessionId(sessionId);
        if (payment == null) {
            throw new Exception("Payment not found for session ID: " + sessionId);
        }

        Contrat contrat = payment.getContrat();
        if (contrat == null) {
            throw new Exception("Contrat not found for payment session ID: " + sessionId);
        }

        if (success) {
            contrat.setPaye(true);
            payment.setStatus("COMPLETED");


            // Update contract dates for renewal if the contract is expired
            if (contrat.getDate_fin_effet().before(new Date())) {
                Date newDateEffet = new Date();
                Date newDateFinEffet = new Date(newDateEffet.getTime() + (365L * 24 * 60 * 60 * 1000)); // Assuming a 1-year extension
                contrat.setDate_effet(newDateEffet);
                contrat.setDate_fin_effet(newDateFinEffet);
                contrat.setRenewed(true);
            }
        } else {
            payment.setStatus("CANCELLED");
        }

        contratRepo.save(contrat);
        paymentRepo.save(payment);
    }

    @Override
    public String renewContract(Long contratId) throws Exception {
        Contrat contrat = getContartById(contratId);
        if (contrat == null) {
            throw new Exception("Contrat not found");
        }

        if (!new Date().after(contrat.getDate_fin_effet())) {
            throw new Exception("Contrat is not expired yet");
        }

        // Create a new payment session for the renewal
        return createPaymentSession(contratId);
    }


    @Override
    public List<Contrat> findExpiredContracts(Long userId) {
        Date currentDate = new Date();
        List<Contrat> expiredContracts = contratRepo.findExpiredContracts(userId, currentDate);

        for (Contrat contrat : expiredContracts) {
            contrat.setPaye(false);
            contrat.setRenewed(false);
            contratRepo.save(contrat);
        }

        return expiredContracts;
    }
}
