function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires=" + d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

function appendCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires=" + d.toUTCString();
    var exCookie = getCookie(cname);
    if (exCookie == "") {
        cvalue = cvalue;
    } else {
        cvalue = exCookie + "," + cvalue;
    }
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

function containsCookie(cname, cvalue) {
    var flag = true;
    var exCookie = getCookie(cname);
    if(exCookie.indexOf(cvalue)==-1){
        flag = false;
    }
    return flag;
}

function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = unescape(document.cookie);
    var ca = decodedCookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function deleteCookie(cname, cvalue) {
    var exCookie = getCookie(cname);
    var cvalueLen = cvalue.length;
    var nvalue = "";
    var targetIdx = 0;
    targetIdx = exCookie.indexOf(cvalue);
    if (targetIdx==0) {
        nvalue = exCookie.slice(cvalueLen+1);
    }else{
        nvalue= exCookie.slice(0, targetIdx-1)+exCookie.slice(targetIdx+cvalueLen);
    }
    setCookie(cname, nvalue);
}