import StarRating from "react-svg-star-rating";

const Tabel = (props) => (
  <div className="TabelBox">
    <div className="TitleLine">
      <div className="stars">
        <StarRating
          isHalfRating={true}
          isReadOnly
          initialRating={props.rating}
        />

        <p className="Data">{props.data}</p>
      </div>
      <p>{props.statie}</p>
    </div>
    <div className="ReviewContent">
      <p>{props.text}</p>
    </div>
  </div>
);

export default Tabel;
