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

    @GetMapping("/carts/{name}")
    public ResponseEntity<Cart> getCartByName(@PathVariable("name") String name) {
        Cart cartData = cartRepository.findByName(name);
        if (cartData.getId() >= 1) {
            logger.info("Cart = "+cartData.toString());
            return new ResponseEntity<>(cartData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/carts")
    public ResponseEntity<Cart> getCartByNamePost(@RequestParam("name") String name) {
        Cart cartData = cartRepository.findByName(name);
        if (cartData.getId() >= 1) {
            logger.info("Cart = "+cartData.toString());
            return new ResponseEntity<>(cartData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addCart")
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart) {
        try {
            Cart _cart = cartRepository
                    .save(new Cart(cart.getName(), cart.getDescription()));
            logger.info("Crearted " + _cart.toString());
            return new ResponseEntity<>(_cart, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.info("Something went wrong!");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addCartByParams")
    public String createCartByParams(@RequestParam(required = true) String name, @RequestParam(required = false) String desc) {
        try {
            Cart _cart = cartRepository.save(new Cart(name, desc));
            logger.info("Crearted " + _cart.toString());
            return "Ok";
        } catch (Exception e) {
            logger.info("Something went wrong!");
            return "Exception";
        }
    }

    @PostMapping("/removeCartByName")
    public String deleteCart(@RequestParam("name") String name) {
        try {
            cartRepository.deleteById(cartRepository.findByName(name).getId());
            return "Ok";
        } catch (Exception e) {
            return "execption";
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
