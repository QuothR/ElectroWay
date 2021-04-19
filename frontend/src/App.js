import logo from './logo.svg';
import './App.css';
import axios from 'axios';
import react, {useState, useEffect} from 'react';

const TestMethod = () => {
  const fetchData = () =>{
    axios.get("http://localhost:8090/").then(res => {
      console.log(res);
    });
  ;}

    useEffect( () => {
      fetchData();
    }, [] );

    return <h1>Test</h1>;
};


function App() {
  return (
    <TestMethod/>
  );
}

export default App;
