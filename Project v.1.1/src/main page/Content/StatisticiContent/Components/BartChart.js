import React from "react";


import {
  BarChart,
  XAxis,
  YAxis,
  Bar,
  CartesianGrid,
} from "recharts";

const Tabel = () => {
  const data = [
    { name: "Ian", suma: 20000 },
    { name: "Feb", suma: 90000 },
    { name: "Mar", suma: 40000 },
    { name: "Apr", suma: 50000 },
    { name: "Mai", suma: 121000 },
    { name: "Iun", suma: 119000 },
    { name: "Iul", suma: 129000 },
    { name: "Aug", suma: 138000 },
    { name: "Sept", suma: 121000 },
    { name: "Oct", suma: 110000 },
    { name: "Nov", suma: 100000 },
    { name: "Dec", suma: 101000 }
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

export default Tabel;