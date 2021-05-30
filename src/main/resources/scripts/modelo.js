var bodyOfPassword = {
    "inputs": {
        "input1": []
    },
    "GlobalParameters": {}
};

var helper = 0;

function GetInputPassword(password) {
    let toReturn = {};
    let a = password;
    let counter = 0;
    helper = 0;
    counter = (/^(?=.*[@#$%^*_&+!=])/g.test(password)) ? 1 : 0;
    let b = (counter);
    helper += counter;
    counter = (/⁾(?=.*[A-Z])/g.test(password)) ? 1 : 0;
    let c = (counter);
    helper += counter;
    counter = (password.lenght >= 8) ? 1 : 0;
    let d = (counter);
    helper += counter;
    counter = (/^(?=.*[0-9])/g.test(password)) ? 1 : 0;
    let e = (counter);
    helper += counter;
    let f = ("strength:" + 0);
    toReturn = {
        "senha": a,
        "special_char": b,
        "upperCase": c,
        ">=8": d,
        "number": e,
        "strength": 0,
    };
    return toReturn;
}
function passwordKeyPress() {
    
    document.getElementById("impForcaSenha").innerHTML = '<span style="color: #ff9900; #ff9900;">Varificando se sua senha ja foi comprometida ...</span>';
    var pass = documen.getElementById("senhaForca-boc");
    bodyOfPassword.inputs.input1[0] = GetInputPassword(pass.value);
    clearTimeout(requestTimeout);
    requestTimeout = setTimeout(passwordmodified, 2000);
    var myinit = {
        method: "POST",
        body: JSON.stringify(bodyOfPassword)
    };
    setTimeout(
        fetch('http://localhost:6789/Senha', myinit)
            .then(resp => resp.json())
            .then((data) => {
                console.log(data.score);
                let subs = document.getElementById("inputGroup.siezing.default");
                let print = "Senha Fraca";
                if (data.score == 1) {
                    print = "Senha Media";
                } else if (data.score == 2) {
                    print = "Senha Forte"
                }
                console.log(print)
                subs.innerHTML = print;
            })

            .catch((err) => {
                let subs = document.getElementById("inputGroup-sizing.default");
                let print = "Senha Fraca";
                if (helper > 1 && helper < 3) {
                    print = "Senha Media";
                } else if (helper == 4) {
                    print = "Senha Forte";
                }
            }), 2000);
}

