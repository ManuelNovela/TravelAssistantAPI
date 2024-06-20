# TravelAssistantAPI
 Travel Assistant - Coding challenge API (Spring Boot) 

# Configuração
Aceitar cors do host e porta do frontend:

```bash
@Override
public void addCorsMappings(CorsRegistry registry){
registry.addMapping("/**")
.allowedOrigins("http://localhost:5173") //este host e porta que o frontend usa
.allowedMethods("GET", "POST");
}
}
```

# Documentação: 
https://documenter.getpostman.com/view/19539378/2sA3XTf1Do#intro




