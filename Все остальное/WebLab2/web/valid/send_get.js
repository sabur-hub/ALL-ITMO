function create_get(){

    const xhr = new XMLHttpRequest();

    let url = new URL('http://localhost:8080/TestAppL2_war_exploded/CServlet');

    url.searchParams.set('y',value_Y);
    url.searchParams.set('r',value_R);

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {

        }
    }
    xhr.open('GET', url);
    xhr.send();


}