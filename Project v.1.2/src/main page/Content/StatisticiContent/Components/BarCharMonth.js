import React from "react";


import {
  BarChart,
  XAxis,
  YAxis,
  Bar,
  CartesianGrid,
} from "recharts";
//function chart(ian,feb,mar,apr,mai,iun,iul,aug,sept,oct,nov,dec){
//function chart(ian,feb,mar,apr,mai,iun,iul,aug,sept,oct,nov,dec)  {

function myChartMonth(first,second,third) {
var data = [
    { name: "1-10", suma: first},
    { name: "11-20", suma: second},
    { name: "20-30", suma: third}
  ];

  return (
    <div style={{ textAlign: "center" }}>

      <div className="App">

        <BarChart
          width={800}
          height={300}
          data={data}
          margin={{
            top: 5,
            right: 30,
            left: 30,
            bottom: 5,
          }}
          barSize={25}
          dataKey="suma"
        >
          <XAxis
            dataKey="name"
            scale="point"
            padding={{ left: 15, right: 15 }}
          />

          <YAxis

            orientation="left"

          />
         
          

          <Bar dataKey="suma" fill="#8884d8" background={{ fill: "#eee" }} />
        </BarChart>
      </div>
    </div>
  );
};
//var Tabel=myChart;
//};
export default myChartMonth;