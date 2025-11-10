package me.aco.marketplace.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import me.aco.marketplace.dto.TransferReq;
import me.aco.marketplace.dto.TransferResp;
import me.aco.marketplace.model.Transfer;
import me.aco.marketplace.repository.TransfersRepository;
import me.aco.marketplace.repository.UsersRepository;
import me.aco.marketplace.service.TransferService;

@Async("asyncExecutor")
@RequestMapping("/Transfer")
@RestController
@RequiredArgsConstructor
public class TransfersController {

    private final TransfersRepository transfersRepository;
    private final UsersRepository usersRepository;
    private final TransferService transferService;

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<List<TransferResp>>> getByUserId(@PathVariable("id") long id) {
        var userOpt = usersRepository.findById(id);
        if (userOpt.isEmpty())
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().build());
        var user = userOpt.get();
        List<Transfer> transfers = transfersRepository.findBySeller(user);
        transfers.addAll(transfersRepository.findByBuyer(user));
        List<TransferResp> resp = transfers.stream().map(TransferResp::new).toList();
        return CompletableFuture.completedFuture(ResponseEntity.ok(resp));
    }

    @PostMapping("/payment")
    public CompletableFuture<ResponseEntity<TransferResp>> addPayment(@RequestBody TransferReq req) {
        var seller = usersRepository.findById(req.getSellerId());
        if (seller.isEmpty())
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().build());
        var transferResp = transferService.addPayment(req, seller.get());
        if (transferResp == null)
            return CompletableFuture.completedFuture(ResponseEntity.internalServerError().build());
        return CompletableFuture.completedFuture(ResponseEntity.ok(transferResp));
    }

    @PostMapping("/withdrawal")
    public CompletableFuture<ResponseEntity<TransferResp>> addWithdrawal(@RequestBody TransferReq req) {
        var buyer = usersRepository.findById(req.getSellerId());
        if (buyer.isEmpty())
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().build());
        var transferResp = transferService.addWithdrawal(req, buyer.get());
        if (transferResp == null)
            return CompletableFuture.completedFuture(ResponseEntity.internalServerError().build());
        return CompletableFuture.completedFuture(ResponseEntity.ok(transferResp)); 
    }

    @PostMapping("/purchase")
    public CompletableFuture<ResponseEntity<TransferResp>> addPurchase(@RequestBody TransferReq req) {
        var buyer = usersRepository.findById(req.getBuyerId());
        if (buyer.isEmpty())
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().build());
        var seller = usersRepository.findById(req.getSellerId());
        if (seller.isEmpty())
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().build());
        var transferResp = transferService.addPurchase(req, buyer.get(), seller.get());
        if (transferResp == null)
            return CompletableFuture.completedFuture(ResponseEntity.internalServerError().build());
        return CompletableFuture.completedFuture(ResponseEntity.ok(transferResp));
    }
    
}
