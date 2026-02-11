import bcrypt from "bcrypt";

import User from "../../modules/user/model/User.js";

export async function createinitialData() {
    try {
        await User.sync({ force: true});

        let password =  await bcrypt.hash("123456", 10);

        await User.create({
            name: "User Teste",
            email: "testeuser@gmail.com",
            password: password
    });
    } catch (error) {
        console.log(error)
    }
    
}