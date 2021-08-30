package org.sid.secservice.sec.web;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.sid.secservice.sec.JWTUtilConst;
import org.sid.secservice.sec.entities.*;
import org.sid.secservice.sec.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AccountRestController {

    private AccountService accountService;

    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(path ="/users")
    public List<AppUser> appUsers(){
        return accountService.listUsers();
    }

    @GetMapping(path = "/visite")
    public List<Visite> appVisites(){
        return accountService.listVisites();
    }

    
    @GetMapping(path = "/employers")
    public List<Employer> appEmployers(){
        return accountService.listEmployers();
    }

    @GetMapping(path = "/visiteur")
    public List<Visiteur> appVisiteur(){
        return accountService.listVisiteurs();
    }

    @GetMapping({"/visite/{id}"})
    public Object appVisitesById(@PathVariable("id") Long id){
        return accountService.listVisitesById(id);
    }

    @GetMapping({"/employers/{id}"})
    public Object appEmployerById(@PathVariable("id") Long id){
        return accountService.listEmployerById(id);
    }

    @GetMapping({"/visiteur_date/{date}"})
    public List<Visiteur> appVisiteursDate(@PathVariable("date") String dateDebutValide) {
        if(dateDebutValide!= null){
            return accountService.findByDateVisiteurs(dateDebutValide);
        }
        return null;
    }

    @GetMapping(path = "/typeVisite")
    public List<TypeVisites> appTypeVisite(Principal principal){
        return accountService.listTypesVisites();
    }

    @GetMapping({ "/visiteurs/{cni}/visites"})
    public Object appvisiteByvisiteur(@PathVariable("cni") Double cni){
        return accountService.listVisiteByVisiteur(cni);
    }

    @GetMapping({"/visite_date/{date}"})
    public List<Visite> appVisiteByDate(@PathVariable("date") String dateEntree){
        if(dateEntree!= null){
            return accountService.findByDataVisite(dateEntree);
        }
        return null;
    }


    //L'annotation @RequestBody nous permet de récupérer le corps de la requête .
    @PostMapping(path ="/users")
    public AppUser saveUser(@RequestBody AppUser appUser){
        return accountService.AddNewUser(appUser);
    }

    @PostMapping(path ="/roles")
    public AppRole saveRole(@RequestBody AppRole appRole){
        return accountService.AddNewRole(appRole);
    }

    @PutMapping(path ="/employers/{id}")
    public Employer saveUpdateEmployer(@PathVariable Long id ,@RequestBody Employer employer){
        return accountService.modifEmployer(id, employer);
    }

    @DeleteMapping(path ="/employers/{id}")
    public Employer deleteEmployer(@PathVariable("id") Long id ){
        return accountService.deleteEmployerById(id);
    }


    @PostMapping(path ="/addRoleToUser")
    public void addRoleToUser(@RequestBody RoleUserForm roleUserForm){
       accountService.addRoleToUser(roleUserForm.getUsername(), roleUserForm.getRoleName());
    }

    @GetMapping(path ="/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String auhToken = request.getHeader(JWTUtilConst.AUTH_HEADER);
        if(auhToken !=null && auhToken.startsWith(JWTUtilConst.PREFIX)){
            try {
                String jwt = auhToken.substring(JWTUtilConst.PREFIX.length());
                Algorithm algorithm = Algorithm.HMAC256(JWTUtilConst.SECRET);
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
                String username = decodedJWT.getSubject();
                AppUser appUser = accountService.loadUserByUsername(username);
                String jwtAccessToken = JWT.create()
                        .withSubject(appUser.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + JWTUtilConst.EXPIRE_ACCESS_TOKEN))
                        .withIssuer(request.getRequestURL().toString())
                        //recuperer liste des roles authorities et convertir en une liste de string
                        .withClaim("roles",appUser.getAppRoles().stream().map(r->r.getRoleName()).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> idToken = new HashMap<>();
                idToken.put("access-token", jwtAccessToken);
                idToken.put("refresh-token", jwt);
                response.setContentType("application/json");
                //getOutputStream() renvoie un flux de sortie pour écrire des octets dans ce socket.
                new ObjectMapper().writeValue(response.getOutputStream(), idToken);

            }catch (Exception e){
               throw e;
            }
        }else {
            throw new RuntimeException("Refresh token required!");
        }
    }

    //L'object Principal permet de connaitre l'utilisateur authentifié
    @GetMapping(path = "/profile")
    public AppUser profile(Principal principal){
       return accountService.loadUserByUsername(principal.getName());
    }

    //Partie Register
    @PostMapping(path = "/register")
    public AppUser register(@RequestBody RegisterForm userForm){
        if(!userForm.getPassword().equals(userForm.getRepassword()))
        throw new RuntimeException("You must confirm you password");

        AppUser user = accountService.findUserByUsername(userForm.getUsername());
        if(user !=null) throw new RuntimeException("This user already exits");
        AppUser appUser = new AppUser();
        appUser.setUsername(userForm.getUsername());
        appUser.setPassword(userForm.getPassword());
        accountService.AddNewUser(appUser);
        accountService.addRoleToUser(userForm.getUsername(), "USER");
        return appUser;
    }

    @PostMapping(path = "/visites")
    public Visite saveVisite(@RequestBody Visite visite){
       return accountService.AddVisites(visite);
    }

    @PostMapping(path = "/employers")
    public Employer saveEmployer(@RequestBody Employer employer){
        return accountService.AddEmployer(employer);
    }

    @PostMapping(path = "/visiteur")
    public Object saveVisiteur(@RequestBody Visiteur visiteur){
        System.out.println((visiteur.getDateFinValide().getTime() - visiteur.getDateDebutValide().getTime())/1000 );
        System.out.println(10*365*24*3600);
        if((visiteur.getDateFinValide().getTime() - visiteur.getDateDebutValide().getTime())/1000 == 10*365*24*3600 || (visiteur.getDateFinValide().getTime() - visiteur.getDateDebutValide().getTime())/1000 == (10*365*24*3600 + 24*3600 )){
            return accountService.AddVisiteurs(visiteur);
        }else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/apprenant")
    public Apprenant saveApprenant(@RequestBody Apprenant apprenant){
        return accountService.AddApprenant(apprenant);
    }

    @PostMapping(path = "/formation")
    public Formation saveFormation(@RequestBody Formation formation){
        return accountService.AddFormation(formation);
    }

}
@Data
class RoleUserForm{
    private String username;
    private String roleName;
}
