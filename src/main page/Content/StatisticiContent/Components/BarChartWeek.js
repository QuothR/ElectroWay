import React from "react";


import {
  BarChart,
  XAxis,
  YAxis,
  Bar,
  CartesianGrid,
} from "recharts";
function myChartWeek(lun,mar,mie,joi,vin,sam,dum) {
var data = [
    { name: "Lun", suma: lun},
    { name: "Mar", suma: mar},
    { name: "Mie", suma: mie},
    { name: "Joi", suma: joi},
    { name: "Vin", suma: vin},
    { name: "Sam", suma: sam},
    { name: "Dum", suma: dum}
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
export default myChartWeek;