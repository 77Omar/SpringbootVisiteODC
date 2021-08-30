package org.sid.secservice.sec.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.sid.secservice.sec.JWTUtilConst;
import org.sid.secservice.sec.entities.AppUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;




public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    //Quant l'utilisateur va tenter de s'authentifier
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("successfulAuthentication");

        //O debut ctai sah
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //Ce code c'est fadilou ki a fait sah to day
         AppUser appUser = new ObjectMapper().readValue(request.getInputStream(), AppUser.class);
         UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(appUser.getUsername(), appUser.getPassword());


        //g commenter ci dessous car on dirait il reconnait pas string request.getParameter line 44 et 45
         //UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
         return authenticationManager.authenticate(authenticationToken);
    }

    //Cas ou l'authentification a reussi
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication");
//getPrincipal return l'utilisateur authentifié
        User user = (User) authResult.getPrincipal();
   //l'agorithme de hachage
        Algorithm alg1= Algorithm.HMAC256(JWTUtilConst.SECRET);

        String jwtAccessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JWTUtilConst.EXPIRE_ACCESS_TOKEN))
                .withIssuer(request.getRequestURL().toString())
                //recuperer liste des roles authorities et convertir en une liste de string
                .withClaim("roles",user.getAuthorities().stream().map(ga->ga.getAuthority()).collect(Collectors.toList()))
                .sign(alg1);

        String jwtRefreshTokenToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JWTUtilConst.EXPIRE_REFRESH_TOKEN))
                .withIssuer(request.getRequestURL().toString())
                .sign(alg1);
        //La HashMap classe a de nombreuses méthodes utiles. Par exemple, pour y ajouter des éléments, utilisez la put()méthode :
        Map<String, String> idToken = new HashMap<>();
        idToken.put("access-token", jwtAccessToken);
        idToken.put("refresh-token", jwtRefreshTokenToken);
        response.setContentType("application/json");
        //getOutputStream() renvoie un flux de sortie pour écrire des octets dans ce socket.
        new ObjectMapper().writeValue(response.getOutputStream(), idToken);

       // super.successfulAuthentication(request, response, chain, authResult);
    }
}
