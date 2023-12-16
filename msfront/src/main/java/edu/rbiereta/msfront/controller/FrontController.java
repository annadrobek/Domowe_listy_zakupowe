package edu.rbiereta.msfront.controller;

import edu.rbiereta.forms.UserPassModel;
import edu.rbiereta.forms.CartModel;
import edu.rbiereta.forms.UserPassEditModel;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author pdrobek
 */
@Controller
@EnableDiscoveryClient
@RequestMapping(path = "/")
public class FrontController {

    @Value("${spring.application.name}")
    String appName;

    @GetMapping("/")
    public String ShowHomePage(Model model) {
        model.addAttribute("appName", appName);
        return "home";
    }

    @RequestMapping("/zaloguj")
    public String ShowSignInPage(Model model) {
        UserPassModel upm = new UserPassModel();
        model.addAttribute("signinform", upm);
        model.addAttribute("appName", appName);
        return "signin";
    }

    @RequestMapping("/sprawdz_usera")
    public String ShowUserPageAfterLogin(Model model, @RequestParam("name") String name, @RequestParam("pass") String pass) {
        String msclientresponse = "";
        msclientresponse = MakePOSTRequest("http://localhost:8080/msclient/api/checkUser", name, pass);
        model.addAttribute("appName", appName);
        if (msclientresponse.equals("1")) {
            model.addAttribute("user", name);
            return "userpage";
        } else if (msclientresponse.equals("2")) {
            model.addAttribute("user", name);
            return "adminpage";
        } else {
            return "userpageError";
        }
    }

    @RequestMapping("/zapisz")
    public String ShowSignUpPage(Model model) {
        UserPassModel upm = new UserPassModel();
        model.addAttribute("signupform", upm);
        model.addAttribute("appName", appName);
        return "signup";
    }

    @RequestMapping("/stworz_usera")
    public String ShowUserPageAfterUserCreation(Model model, @RequestParam("name") String name, @RequestParam("pass") String pass) {
        String msclientresponse = "";
        msclientresponse = MakePOSTRequest("http://localhost:8080/msclient/api/addUser", name, pass);
        model.addAttribute("appName", appName);
        if (msclientresponse.equals("Ok")) {
            model.addAttribute("user", name);
            return "userpage";
        } else {
            return "userpageError";
        }
    }

    @RequestMapping("/stworz_koszyk")
    public String CreateCart(Model model, @RequestParam("name") String name, @RequestParam("desc") String desc) {
        String mscartresponse = "";
        mscartresponse = MakePOSTRequest("http://localhost:8080/mscart/api/addCartByParams", name, desc);
        model.addAttribute("appName", appName);
        if (mscartresponse.equals("Ok")) {
            model.addAttribute("user", "user");
            return "userpage";
        } else {
            return "userpageError";
        }
    }

    @RequestMapping("/addcart")
    public String AddCart(Model model) {
        CartModel cm = new CartModel();
        model.addAttribute("addcartform", cm);
        model.addAttribute("appName", appName);
        return "addcart";
    }

    @RequestMapping("/removecart")
    public String RemoveCart(Model model) {
        CartModel cm = new CartModel();
        model.addAttribute("removecartform", cm);
        model.addAttribute("appName", appName);
        return "removecart";
    }

    @RequestMapping("/edituser")
    public String EditUser(Model model) {
        UserPassEditModel upem = new UserPassEditModel();
        upem.setOldName("user");
        upem.setOldPass("pass");
        model.addAttribute("edituserform", upem);
        model.addAttribute("appName", appName);
        model.addAttribute("oldName", "user");
        model.addAttribute("oldPass", "pass");
        return "edituser";
    }

    @RequestMapping("/edytuj_usera")
    public String EditUser(Model model, @RequestParam("name") String name, @RequestParam("pass") String pass, @RequestParam("oldName") String oldname, @RequestParam("oldPass") String oldpass) {
        String msclient = "";
        msclient = MakePOSTRequest("http://localhost:8080/msclient/api/editUserByName", name, pass, oldname, oldpass);
        model.addAttribute("appName", appName);
        if (msclient.equals("Ok")) {
            model.addAttribute("user", name);
            return "userpage";
        } else {
            return "userpageError";
        }
    }

    String MakePOSTRequest(String destination, String param1, String param2) {
        String finalresponse = "";
        try {
            String form = Map.of("name", param1, "pass", param2)
                    .entrySet()
                    .stream()
                    .map(entry -> String.join("=",
                    URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8),
                    URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8)))
                    .collect(Collectors.joining("&"));
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(destination + "?" + form))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            finalresponse = response.body();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        }
        return finalresponse;
    }
    
    String MakePOSTRequest(String destination, String param1, String param2, String param3, String param4) {
        String finalresponse = "";
        try {
            String form = Map.of("name", param1, "pass", param2, "oldname", param3, "oldpass", param4)
                    .entrySet()
                    .stream()
                    .map(entry -> String.join("=",
                    URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8),
                    URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8)))
                    .collect(Collectors.joining("&"));
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(destination + "?" + form))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            finalresponse = response.body();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        }
        return finalresponse;
    }
}
