package me.aco.marketplace.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.aco.marketplace.dto.TransferReq;
import me.aco.marketplace.dto.TransferResp;
import me.aco.marketplace.enums.TransferType;
import me.aco.marketplace.model.Transfer;
import me.aco.marketplace.model.User;
import me.aco.marketplace.repository.TransfersRepository;
import me.aco.marketplace.repository.UsersRepository;

@Service
public class TransferService {

    @Autowired
    private TransfersRepository transfersRepository;
    @Autowired
    private UsersRepository usersRepository;

    public TransferResp addPayment(TransferReq request, User seller) {
        Transfer transfer = new Transfer();
        transfer.setSeller(seller);
        transfer.setAmount(request.getAmount());
        transfer.setType(TransferType.Payment);
        transfer.setCreatedAt(LocalDateTime.now());
        seller.setBalance(seller.getBalance() + request.getAmount());
        transfersRepository.save(transfer);
        usersRepository.save(seller);
        return new TransferResp(transfer);
    }

    public TransferResp addWithdrawal(TransferReq request, User buyer) {
        Transfer transfer = new Transfer();
        transfer.setSeller(buyer);
        transfer.setAmount(request.getAmount());
        transfer.setType(TransferType.Withdrawal);
        transfer.setCreatedAt(LocalDateTime.now());
        buyer.setBalance(buyer.getBalance() - request.getAmount());
        transfersRepository.save(transfer);
        usersRepository.save(buyer);
        return new TransferResp(transfer);
    }

    public TransferResp addPurchase(TransferReq request, User buyer, User seller) {
        Transfer transfer = new Transfer();
        transfer.setSeller(seller);
        transfer.setBuyer(buyer);
        transfer.setAmount(request.getAmount());
        transfer.setType(TransferType.Purchase);
        transfer.setCreatedAt(LocalDateTime.now());
        buyer.setBalance(buyer.getBalance() - request.getAmount());
        seller.setBalance(seller.getBalance() + request.getAmount());
        transfersRepository.save(transfer);
        usersRepository.save(buyer);
        usersRepository.save(seller);
        return new TransferResp(transfer);
    }
    
}
