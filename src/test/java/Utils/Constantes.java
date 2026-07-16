package Utils;


import io.restassured.http.ContentType;

public interface Constantes {

    String APP_BASE_URL = "https://serverest.dev";
    // String APP_BASE_URL = "http://localhost:3000";
    String APP_BASE_PATH = "usuarios";
    String APP_BASE_PATH_PRODUTOS = "produtos";
    String APP_BASE_PATH_CARRINHOS = "carrinhos";
    String CARRINHO_CONCLUIR_COMPRA ="carrinhos/concluir-compra";
    String CARRINHO_CANCELAR_COMPRA = "carrinhos/cancelar-compra";
    String CARRINHOS_= "/carrinhos/";
    String CARRINHOS = "/carrinhos";
    String _ID = "_id";

    ContentType APP_CONTENT_TYPE = ContentType.JSON;

}
