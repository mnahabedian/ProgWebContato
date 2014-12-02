/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var nome;
var dtNasc;
var dtTeste;
var facebook;
var twitter;
var site;

function enviar() {
    alert("Enviei...");
    
    submit();
    
    return true;
}

function validar() {
    facebook = document.getElementById("facebook");
    if(facebook.value != "" && !verificaFacebook(facebook.value)){
        facebook.setCustomValidity('Preencha o campo corretamente!\nExemplo: https://www.facebook.com/xxxxxxx');
    }
    else{
        facebook.setCustomValidity('');
    }
    
    twitter = document.getElementById("twitter");
    if(twitter.value != "" && !verificaTwitter(twitter.value)){
        twitter.setCustomValidity('Preencha o campo corretamente!\nExemplo: https://twitter.com/xxxxxxx');
    }
    else{
        twitter.setCustomValidity('');
    }
    
    site = document.getElementById("site");
    if(site.value != "" && !verificaSite(site.value)){
        site.setCustomValidity('Preencha o campo corretamente!\nExemplo: http(s)://xxxxxxxxxxx');
    }
    else{
        site.setCustomValidity('');
    }
}

function verificaFacebook(endereco){
    maskFace = "https://www.facebook.com/";
    if(endereco.length > maskFace.length){
        for(i = 0; i < maskFace.length; i++){
            if(endereco[i] != maskFace[i])
                return false;
        }
    }
    else
        return false;
    
    return true;
}

function verificaTwitter(endereco){
    maskTwitter = "https://twitter.com/";
    if(endereco.length > maskTwitter.length){
        for(i = 0; i < maskTwitter.length; i++){
            if(endereco[i] != maskTwitter[i])
                return false;
        }
    }
    else
        return false;
    
    return true;
}

function verificaSite(endereco){
    maskSite = "http://";
    maskSite2 = "https://";
    max = maskSite.length;
    if(endereco.length > maskSite2.length){
        for(i = 0; i < max; i++){
            if(endereco[i] != maskSite[i] && endereco[i] != maskSite2[i])
                return false;
            if(endereco[i] != maskSite[i])
                max = maskSite2.length;
        }
    }
    else
        return false;
    
    return true;
}

/*
 * Ajax
 */
function createRequestObject() {
    var req;

    if(window.XMLHttpRequest)
    {
        //For Firefox, Safari, Opera
        req = new XMLHttpRequest();
    }
    else if(window.ActiveXObject)
    {
        //For IE 5+
        req = new ActiveXObject("Microsoft.XMLHTTP");
    }
    else
    {
        //Error for an old browser
        alert('Your browser is not IE 5 or higher, or Firefox or Safari or Opera');
    }

    return req;
}

//Make the XMLHttpRequest Object
var http = createRequestObject();

function sendRequest(method, url)
{
    if(method == 'get' || method == 'GET')
    {
        http.open(method,url);
        http.onreadystatechange = handleResponse;
        http.send(null);
    }
}

function handleResponse()
{
    if(http.readyState == 4 && http.status == 200)
    {
        var response = http.responseText;
        if(response)
        {
            document.getElementById("idCidade").innerHTML = response;
        }
    }
}

function dropdownCidade(SelectedValue)
{
    sendRequest('GET', 'CidadeServlet?idEstado=' + SelectedValue);
}
