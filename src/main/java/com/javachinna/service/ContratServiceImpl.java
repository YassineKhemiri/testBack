package com.javachinna.service;

import com.javachinna.model.Contrat;
import com.javachinna.model.CountType;
import com.javachinna.model.User;
import com.javachinna.repo.ContratRepo;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContratServiceImpl implements ContratService{
    @Autowired
    private StripeService stripeService;

    @Autowired
    private ContratRepo contratRepo;
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
        // TODO Auto-generated method stub
        Optional<Contrat> o= contratRepo.findById(id);
        return o.isPresent() ? o.get() :null;
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
            Session session = stripeService.createSession(amount, contractName);
            contrat.setPaye(true);
            contratRepo.save(contrat);
            return session.getId();

        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            throw new Exception("Failed to create payment session: " + e.getMessage(), e);
        }
    }
}
