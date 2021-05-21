import React from "react";
import Tabel from "./Tabel";
import DateTabel from "./DateTabelLasate.json";
import Slider from "react-animated-slider";
import "react-animated-slider/build/horizontal.css";

function ElementeJosStg() {
  return (
    <div className="ElementeJos">
      <Slider>
        {DateTabel.map((val, key) => {
          return (
            <div className="Mijloc" key={key}>
              <Tabel rating={val.rating} data={val.data} statie={val.statie} text={val.text} />
            </div>
          );
        })}
      </Slider>
    </div>
  );
}

export default ElementeJosStg;
