package edu.adrobek.mscart.controller;

import edu.adrobek.mscart.repo.Cart;
import edu.adrobek.mscart.repo.CartRepo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping(value = "/api")
public class CartController {

    private static final Logger logger = LogManager.getLogger(CartController.class);

    @Autowired
    CartRepo cartRepository;

    @GetMapping("/carts")
    public ResponseEntity<List<Cart>> getAllCarts(@RequestParam(required = false) String name) {
        try {
            List<Cart> carts = new ArrayList<Cart>();

            if (name == null) {
                cartRepository.findAll().forEach(carts::add);
            } else {
                cartRepository.findByNameContaining(name).forEach(carts::add);
            }
            if (carts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(carts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/carts/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable("id") int id) {
        Optional<Cart> cartData = cartRepository.findById(id);

        if (cartData.isPresent()) {
            return new ResponseEntity<>(cartData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addCart")
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart) {
        try {
            Cart _cart = cartRepository
                    .save(new Cart(cart.getName(), cart.getDescription()));
            return new ResponseEntity<>(_cart, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addCartByParams")
    public String createCartByParams(@RequestParam(required = true) String name, @RequestParam(required = false) String desc) {
        try {
            Cart _cart = cartRepository.save(new Cart(name, desc));
            return "Ok";
        } catch (Exception e) {
            return "Exception";
        }
    }

    @PutMapping("/carts/{id}")
    public ResponseEntity<Cart> updateCart(@PathVariable("id") int id, @RequestBody Cart cart) {
        Optional<Cart> cartData = cartRepository.findById(id);

        if (cartData.isPresent()) {
            Cart _cart = cartData.get();
            _cart.setName(cart.getName());
            _cart.setDescription(cart.getDescription());
            return new ResponseEntity<>(cartRepository.save(cart), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/carts/{id}")
    public ResponseEntity<HttpStatus> deleteCart(@PathVariable("id") int id) {
        try {
            cartRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/carts")
    public ResponseEntity<HttpStatus> deleteAllCarts() {
        try {
            cartRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
