import eng from "./locales/en/translation.json"
import ro from "./locales/ro/translation.json"

class MyTranslator {
    static staticProperty = "RO"; 
    // const component;

    constructor(component= "None") {
    this.component = component;
    }
    
    useTranslation (value)  {

    switch(MyTranslator.staticProperty) {
      case "GB":

        if (this.component) {
          if (value in eng[this.component]) {
            return eng[this.component][value];
          } 
        }

        if (value in eng["common"]) {
          return eng["common"][value];
        }

        return "Error: " + value;
      case "RO":

        if (ro[this.component]) {
          if (value in ro[this.component]) {
            return ro[this.component][value];
          }
        }

        if (value in ro["common"]) {
          return ro["common"][value];
        }

        return "Error: " + value;
      default:
        console.log(MyTranslator.staticProperty);
        return "Error-Language-Not-Found";
    } 

  }
}

export default MyTranslator;