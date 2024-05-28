package com.javachinna.service;

import com.javachinna.model.Contrat;
import com.javachinna.model.CountType;
import com.javachinna.model.User;

import java.util.List;

public interface ContratService {

    public List<Contrat> getAllContarts();

    public List<Contrat> findContratByUserId(Long id);


    public Contrat getContartById(Long id);

    public void deleteContart(Long id);

    public List<CountType> getPercentageGroupByBranche();
    public String createPaymentSession(Long contratId) throws Exception;
    public void updatePaymentStatus(String sessionId, boolean success) throws Exception;
    public String renewContract(Long contratId) throws Exception;
    public List<Contrat> findExpiredContracts(Long userId) ;
   /*  public void handlePaymentSuccess(String sessionId);
   public String createPaymentSession(Long contratId, Long userId, String signature) throws Exception;*/
}
