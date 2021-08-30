package org.sid.secservice.sec.service;

import org.sid.secservice.sec.entities.*;

import java.util.List;

public interface AccountService {
    AppUser AddNewUser(AppUser appUser);
    AppRole AddNewRole(AppRole appRole);
    void addRoleToUser(String username, String roleName);
    AppUser loadUserByUsername(String username);
    Visite AddVisites (Visite visite);
    Visiteur AddVisiteurs (Visiteur visiteur);
    Employer AddEmployer (Employer employer);
    Apprenant AddApprenant(Apprenant apprenant);
    Formation AddFormation(Formation formation);

    List<AppUser> listUsers();
    List<Visiteur> listVisiteurs();
    List<Employer> listEmployers();
    List<TypeVisites> listTypesVisites();
    List<Visite> listVisites();
    Visite listVisitesById(Long id);
    Employer listEmployerById(Long id);
    Employer deleteEmployerById(Long id);
    List<Visiteur> findByDateVisiteurs(String date);
    List<Visite> listVisiteByVisiteur(Double cni);
    List<Visite> findByDataVisite(String date);
    Employer modifEmployer(Long id ,Employer employer);

    AppUser findUserByUsername(String username);

}
