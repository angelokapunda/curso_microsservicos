import express from "express";

import { connect } from "./src/config/db/mongoDbConfig.js";
import { createInitialData } from "./src/config/db/initialData.js";
import CheckToken from "./src/config/auth/CheckToken.js";



const app = express();
const env = process.env;
const PORT = env.PORT || 8082;

connect();
createInitialData();

app.use(CheckToken);

app.get("/app/status", async(req, res) => {
    return res.status(200).json({
        service: "Sales-API",
        status: "up",
        httpStatus: 200
    });
});

app.listen(PORT, () => {
    console.log(`Server started successfully at pot ${PORT}`)
});