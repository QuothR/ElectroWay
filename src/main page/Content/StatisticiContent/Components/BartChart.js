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

function myChart(ian, feb, mar, apr, mai, iun, iul, aug, sep, oct, nov, dec) {
  var data = [
    { name: "Ian", suma: ian },
    { name: "Feb", suma: feb },
    { name: "Mar", suma: mar },
    { name: "Apr", suma: apr },
    { name: "Mai", suma: mai },
    { name: "Iun", suma: iun },
    { name: "Iul", suma: iul },
    { name: "Aug", suma: aug },
    { name: "Sept", suma: sep },
    { name: "Oct", suma: oct },
    { name: "Nov", suma: nov },
    { name: "Dec", suma: dec }
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
export default myChart;