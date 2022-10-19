package br.com.rafaellbarros.jakartaee.ejb.service;


import javax.ejb.Stateless;

@Stateless
public class XablauService {

    public String xablau() {
        return "Xablau!!!";
    }
}
