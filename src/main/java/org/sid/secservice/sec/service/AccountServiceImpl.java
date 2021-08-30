package org.sid.secservice.sec.service;

import org.sid.secservice.sec.entities.*;
import org.sid.secservice.sec.repo.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private AppVisiteurRepository appVisiteurRepository;
    private AppApprenantRepository appApprenantRepository;
    private AppVisitRepository appVisitRepository;
    private AppTypesVisitesRepository appTypesVisitesRepository;
    private PasswordEncoder passwordEncoder;
    private AppFormationRepository appFormationRepository;
    private AppEmploieRepository appEmploieRepository;

    public AccountServiceImpl(AppUserRepository appUserRepository, AppVisiteurRepository appVisiteurRepository,
                              AppRoleRepository appRoleRepository,
                              AppApprenantRepository appApprenantRepository, AppVisitRepository appVisitRepository, AppTypesVisitesRepository appTypesVisitesRepository, PasswordEncoder passwordEncoder, AppFormationRepository appFormationRepository, AppEmploieRepository appEmploieRepository) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
        this.appVisitRepository = appVisitRepository;
        this.appTypesVisitesRepository = appTypesVisitesRepository;
        this.passwordEncoder = passwordEncoder;
        this.appVisiteurRepository = appVisiteurRepository;
        this.appApprenantRepository = appApprenantRepository;
        this.appFormationRepository = appFormationRepository;
        this.appEmploieRepository = appEmploieRepository;
    }

    @Override
    public AppUser AddNewUser(AppUser appUser) {
        String pw = appUser.getPassword();
        appUser.setPassword(passwordEncoder.encode(pw));
       return appUserRepository.save(appUser);
    }

    @Override
    public AppRole AddNewRole(AppRole appRole) {
        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser appUser = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        appUser.getAppRoles().add(appRole);
    }

    @Override
    public Visite AddVisites (Visite visite){
        return appVisitRepository.save(visite);
    }

    @Override
    public Visiteur AddVisiteurs (Visiteur visiteur){
        return appVisiteurRepository.save(visiteur);
    }

    @Override
    public Employer AddEmployer (Employer employer){
        return appEmploieRepository.save(employer);
    }

    @Override
    public Apprenant AddApprenant (Apprenant apprenant){
        return appApprenantRepository.save(apprenant);
    }

    @Override
    public Formation AddFormation(Formation formation){
        return appFormationRepository.save(formation);
    }

    @Override
    public Employer modifEmployer(Long id ,Employer employer){
        return appEmploieRepository.findById(id)
                .map(emploie -> {
                    emploie.setPrenom(employer.getPrenom());
                    emploie.setNom(employer.getNom());
                    emploie.setPhone(employer.getPhone());
                    emploie.setEmail(employer.getEmail());
                    emploie.setService(employer.getService());
                    return appEmploieRepository.save(emploie);
                })
                .orElseGet(()-> appEmploieRepository.save(employer));
    }
    
    @Override
    public Employer deleteEmployerById(Long id){
         appEmploieRepository.deleteById(id);
        return null;
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public List<AppUser> listUsers() {
        return (List<AppUser>) appUserRepository.findAll();
    }

    @Override
    public List<Visiteur> listVisiteurs() {
        return (List<Visiteur>) appVisiteurRepository.findAll();
    }

    @Override
    public  List<Employer> listEmployers(){
        return (List<Employer>) appEmploieRepository.findAll();
    }

    @Override
    public AppUser findUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public List<Visite> listVisites(){
        return (List<Visite>) appVisitRepository.findAll();
    }

    @Override
    public Visite listVisitesById(Long id){
        return appVisitRepository.findById(id).get();
    }

    @Override
    public Employer listEmployerById(Long id){
        return appEmploieRepository.findById(id).get();
    }

    @Override
    public List<Visiteur> findByDateVisiteurs(String date){
       List<Visiteur> visiteurs = (List<Visiteur>) appVisiteurRepository.findAll();
       List<Visiteur> visiteurs1 = new ArrayList<>();
       for (Visiteur visiteur: visiteurs){
           if(date.equals(visiteur.getDateDebutValide().toString().substring(0,10)))
               visiteurs1.add(visiteur);
       }
       return visiteurs1;
    }

     @Override
     public List<Visite> listVisiteByVisiteur(Double cni){
        Visiteur visiteur = appVisiteurRepository.findByCni(cni);
         return appVisitRepository.findByVisiteur(visiteur);
     }

     @Override
    public  List<TypeVisites> listTypesVisites(){
        return (List<TypeVisites>) appTypesVisitesRepository.findAll();
     }

     @Override
    public List<Visite> findByDataVisite(String date){
        List<Visite> visites = (List<Visite>) appVisitRepository.findAll();
        List<Visite> visitesByDate = new ArrayList<>();
        for (Visite visite: visites){
            if (date.equals(visite.getDateEntree().toString().substring(0,10)))
                visitesByDate.add(visite);
        }
        return visitesByDate;
     }

}
