function Calculadora(){


        let acum = 0
        let number = ''
        let operation = '+'

        let clickNumber = ev =>{
            let number = ev.target.value
            console.log(number)
            if(localStorage.selected=='first'){
                localStorage.first+=number
                console.log(localStorage.first)
            }else{
                localStorage.second+=number
                console.log(localStorage.second)
            }
            // console.log(ev, "mensaje", ev.target, ev.target.value)
        }
        
        let clickOperation = ev =>{
            let operations = ev.target.value
            console.log(operations)
        }

        let clickCalculate = ev =>{
            console.log("calculate")
        }

        let btnSum = document.querySelector('#btnSum')
        btnSum.addEventListener('click', clickOperation);
        let btnCalculate = document.querySelector('#btnCalculate')
        btnCalculate.addEventListener('click', clickCalculate);
        let buttons = []

        for(let i = 0; i <= 9; i++) {
            buttons[i] = document.querySelector(`#bt${i}`)
            buttons[i].addEventListener('click', clickNumber);
        }



}