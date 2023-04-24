import { titleCase } from "./formateadores";

describe('Pruebas de titleCase', () =>{
    [
        ['MINUSCULAS','Minusculas'],
        ['minusculaS','Minusculas'],
        ['minuSCulas','Minusculas'], 
        ['minuSCulas   ','Minusculas   ']
    ].forEach(caso =>{
        it(`titleCase ${caso[0]}`, ()=>{
            expect(titleCase(caso[0])).toBe(caso[1])
        })
    })

})