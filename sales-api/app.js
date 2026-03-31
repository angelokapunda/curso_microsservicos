import express from "express";

import { connect } from "./src/config/db/mongoDbConfig.js";
import Order from "./src/modules/sales/model/Order.js";


const app = express();
const env = process.env;
const PORT = env.PORT || 8082;

connect();

app.get("/app/status", async(req, res) => {
    let teste = await Order.find();
    return res.status(200).json({
        service: "Sales-API",
        status: "up",
        httpStatus: 200
    });
});

app.listen(PORT, () => {
    console.log(`Server started successfully at pot ${PORT}`)
});