//var easyinvoice = require('easyinvoice');
import easyinvoice from 'easyinvoice';
function downloadInvoice(data,destinatar,suma,cod) {
 
    data = {
        "documentTitle": "Receipt", //Defaults to INVOICE
        "currency": "RON",
        
        "marginTop": 25,
        "marginRight": 25,
        "marginLeft": 25,
        "marginBottom": 25,
        "logo":"https://i.postimg.cc/SKryQgLY/logo3.png", //or base64
        //"logoExtension": "png", //only when logo is base64
        "sender": {
            "company": "Electro way",
            "address": "Str. U.N.K.N.O.W.N",
            "zip": "70021",
            "city": "Iasi",
            "country": "Romania"
            //"custom1": "custom value 1",
            //"custom2": "custom value 2",
            //"custom3": "custom value 3"
        },
        "client": {
            "company": destinatar,
            "address": "B-dul Carol,aleea regala",
            "zip": "705200",
            "city": "Iasi",
            "country": "Romania",
            "email": "ancaancutza@gmamil.com",
            //"custom2": "custom value 2",
            //"custom3": "custom value 3"
        },
        "invoiceNumber": cod,
        "invoiceDate": data,
        "products": [
            {
                "quantity": "1",
                "description": "Charging",
                "tax": 0,
                "price": suma
            },
            // {
            //     "quantity": "4",
            //     "description": "Test2",
            //     "tax": 21,
            //     "price": 10.45
            // }
        ],
        "bottomNotice": "Thank you for using our services"
    };

    //Create your invoice! Easy!
    easyinvoice.createInvoice(data,function (result) {
        //The response will contain a base64 encoded PDF file
        //console.log(result.pdf);
        // fs.writeFileSync("pay.pdf",pay.pdf,'base64');
        easyinvoice.download("receipt.pdf")
    });
    //const result=await easyinvoice.createInvoice(data);
    //easyinvoice.download('receip.pdf',result.pdf);

}
export default downloadInvoice;