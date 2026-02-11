import User from "../model/User";

class UserRepository{
    async findById(id) {
        try {
            return await User.findOne({where: {id}});
        } catch (error) {
            console.log(error.messege);
            return null;
            
        }
    }

    async findByEmail(email) {
        try {
            return await User.findOne({where: {email}});
        } catch (error) {
            console.log(error.messege);
            return null;
            
        }
    }
}


export default new UserRepository();