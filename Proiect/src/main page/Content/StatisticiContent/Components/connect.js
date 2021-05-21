import React from "react";
import myChart from './BartChart.js';
import myChartWeek from './BarChartWeek.js'
import myChartMonth from './BarCharMonth.js'
import Tabel from './BartChart.js';
import Weekjson from './Weekjson.json'
function showBar(value){
    //console.log('sunt prost 1');
    var Tabel;
//return Tabel(2000,9000,4000,5000,12000,11000,12000,13000,12000,11000,18000,10000);
switch(value){
    case 'saptamanal':  
    console.log('sunt si mai prost');   
     //parsare json
 var result = [];//
 Weekjson.map(val=>(
result.push(val.valoare)

 ))

console.log(result);
 Tabel=myChartWeek(result[0],result[1],result[2],result[3],result[4],result[5],result[6]);
 break;
 case 'anual':
     console.log("anual");
    Tabel=myChart(4000,4000,4000,4000,12000,11000,12000,4000,12000,11000,4000,10000);
    break;
default:    
console.log("default");
Tabel=myChartMonth(1000,500,200);
}
return (Tabel);
}
export default showBar;