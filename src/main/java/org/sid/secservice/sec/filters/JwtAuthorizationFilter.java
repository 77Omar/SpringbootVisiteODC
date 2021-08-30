package org.sid.secservice.sec.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.sid.secservice.sec.JWTUtilConst;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;


public class JwtAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, POST, PUT, DELETE");
        response.addHeader("Access-Control-Allow-Headers",
                "Origin, Accept, X-Requested-With, Content-Type, " +
                        "Access-Control-Request-Method, " +
                        "Access-Control-Request-Headers, " +
                        "Authorization");
        response.addHeader("Access-Control-Expose-Headers",
                "Access-Control-Allow-Origin, " +
                        "Access-Control-Allow-Credentials, " +
                        "Authorization");

        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            //doFilterInternal intercepte les requêtes puis vérifie l'entête Authorization
            //getServletPath : renvoie la partie de l'URL de la requête qui appelle le servlet

            if (request.getServletPath().equals("/refreshToken")) {
                filterChain.doFilter(request, response);
            } else {
                String authorizationToken = request.getHeader(JWTUtilConst.AUTH_HEADER);
                System.out.println(request);
                if (authorizationToken != null && authorizationToken.startsWith(JWTUtilConst.PREFIX)) {
                    try {
                        String jwt = authorizationToken.substring(JWTUtilConst.PREFIX.length());
                        Algorithm algorithm = Algorithm.HMAC256(JWTUtilConst.SECRET);
                        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                        DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
                        //Recuperer username dans getSubject
                        String username = decodedJWT.getSubject();
                        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                        //Conversion GrantedAuthority
                        Collection<GrantedAuthority> authorities = new ArrayList<>();
                        for (String r : roles) {
                            authorities.add(new SimpleGrantedAuthority(r));
                        }
                        //Authentifier L'utilisateur
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(username, null, authorities);
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        //Une fois authentifier on peur utiliser le filterChain pour dire tu px passer o suivant
                        filterChain.doFilter(request, response);

                    } catch (Exception e) {
                        response.setHeader("error-message", e.getMessage());
                        response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    }

                } else {
                    filterChain.doFilter(request, response);
                }
            }
        }
    }
}
