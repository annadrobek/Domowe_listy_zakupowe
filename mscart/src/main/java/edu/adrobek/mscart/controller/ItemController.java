package edu.adrobek.mscart.controller;

import edu.adrobek.mscart.repo.Cart;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import edu.adrobek.mscart.repo.Item;
import edu.adrobek.mscart.repo.CartRepo;
import edu.adrobek.mscart.repo.ItemRepo;
import java.util.Optional;
import java.util.function.Supplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api")
public class ItemController {

    private static final Logger logger = LogManager.getLogger(ItemController.class);

    @Autowired
    private CartRepo cartRepository;
    @Autowired
    private ItemRepo itemRepository;

    @GetMapping("/getCartById")
    public ResponseEntity<List<Item>> getAllItemsByCartId(@RequestParam(value = "cart_id") int cart_id) {
        if (!cartRepository.existsById(cart_id)) {
            logger.error("Not found Cart with id = " + cart_id);
        }
        List<Item> items = itemRepository.findByCartId(cart_id);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PostMapping("/addItemToCart")
    public ResponseEntity<Cart> createCart(@RequestParam String cart_name, @RequestParam String cart_description, @RequestParam String item_content) {
        if (cartRepository.existsById(cartRepository.findByName(cart_name).getId())) {
            Cart _cart = cartRepository.findByName(cart_name);
            Item _item = itemRepository.save(new Item(_cart, item_content));
            return new ResponseEntity<>(_cart, HttpStatus.CREATED);
        } else {
            Cart _cart = cartRepository.save(new Cart(cart_name, ""));
            Item _item = itemRepository.save(new Item(_cart, item_content));
            return new ResponseEntity<>(_cart, HttpStatus.CREATED);
        }
        //return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
