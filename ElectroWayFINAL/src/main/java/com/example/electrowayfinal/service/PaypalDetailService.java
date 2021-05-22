package com.example.electrowayfinal.service;

import com.example.electrowayfinal.models.PaypalDetail;
import com.example.electrowayfinal.models.User;
import com.example.electrowayfinal.repositories.PaypalDetailRepository;
import com.example.electrowayfinal.repositories.RoleRepository;
import com.example.electrowayfinal.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class PaypalDetailService {

    @Autowired
    PaypalDetailRepository paypalDetailRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserService userService;

    private String secret;

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    public PaypalDetail addPaypalDetail(PaypalDetail paypalDetail, HttpServletRequest httpServletRequest) throws Exception {
        User user = JwtUtil.getUserFromToken(userService, secret, httpServletRequest);
        paypalDetail.setUser(user);
        if(paypalDetailRepository.findByUser_Id(paypalDetail.getUser().getId()).isPresent()){
            throw new NoSuchFieldException("This user already has a wallet");
        }
        paypalDetailRepository.save(paypalDetail);
        return paypalDetailRepository.findByUser_Id(paypalDetail.getUser().getId()).get();
    }

    public void updatePaypalDetail(PaypalDetail paypalDetail, HttpServletRequest httpServletRequest) throws Exception {
        User user = JwtUtil.getUserFromToken(userService, secret, httpServletRequest);
        PaypalDetail paypalDetailToChange = paypalDetailRepository.findByUser_Id(user.getId()).orElseThrow(() -> new Exception("Can't find paypal detail for ID " + user.getId()));

        paypalDetailToChange.setClientId(paypalDetail.getClientId());
        paypalDetailToChange.setSecret(paypalDetail.getSecret());
        paypalDetailRepository.save(paypalDetailToChange);
    }

    public PaypalDetail getPaypalDetailByOwnerId(Long id) throws Exception {
        if(paypalDetailRepository.findByUser_Id(id).isEmpty()){
            throw new Exception("No user with the id" + id);
        }
        return paypalDetailRepository.findByUser_Id(id).get();
    }
}
