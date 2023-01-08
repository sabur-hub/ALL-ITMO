let r;

document.getElementById("R_field")
    .addEventListener('input', e => {
        check_R();
        r = inputR.value;
    })


document.getElementById("graphic").onclick = function(event) {
    const rect = document.getElementById("graphic").getBoundingClientRect();

    if ((r>=1 && r<=4)) {

        const x = ((event.clientX - rect.left -125)/(18*5)*r);
        const y = (((- event.clientY) + rect.bottom -125)/(18*5)*r);
        let xt = (event.clientX - rect.left);
        let yt = (( event.clientY) - rect.top );

        changePoint(xt,yt)
        let inputY = document.getElementById("Y_field");
        inputY.value = y.toString() ;
        let inputx = document.getElementById("X_field");
        inputx.value = x.toString() ;
       $("#submit2").click()

    } else {
      alert("I can't check your point \n" +
          "Please check R ")
    }
}


function changePoint(x,y) {
    let n = document.getElementById("table_out").getElementsByTagName("tr").length
    let point = $("#point");
    point.attr({
        cx: x,
        cy: y,
        visibility: "visible"
    });
}