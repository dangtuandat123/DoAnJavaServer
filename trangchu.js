
var initialPosition = 0;
var isMoved = false;
// hàm bật menu bên trái lên
function batMenuBenTrai() {
    var box = document.querySelector('#menuBenTrai');
    var box2 = document.querySelector('#bgKhiBatMenuBenTrai');
    if(isMoved == false){
       
        box.style.left = '0px';
        box2.style.left = '50%';
        isMoved=true;
    } 
    else if(isMoved==true){

        box.style.left = '-50%';
        box2.style.left = '-50%';
        isMoved = false
    } 
  }


