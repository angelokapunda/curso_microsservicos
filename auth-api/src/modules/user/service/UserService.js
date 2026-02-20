import UserRepository from "../repository/UserRepository.js"
import * as HttpStatus from "../../../config/constants/HttpStatus.js"
import * as Secrets from "../../../config/constants/Secrets.js";
import UserException from "../exception/UserException.js";

import bcrypt from "bcrypt";
import jwt from "jsonwebtoken";

class UserService {
    async findByEmail(req) {
        try {
            const { email } = req.params;
            const { authUser } = req;
            this.validateRequestData(email);
            let user = await UserRepository.findByEmail(email);
            this.validateUserNotFound(user);
            this.validateAuthenticatedUser(user, authUser)           
            return {
                status: HttpStatus.SUCCESS,
                user : {
                    id: user.id,
                    name: user.name,
                    email: user.email
                }
            };

        } catch (error) {
            return {
                status: error.status ? error.status : HttpStatus.INTERNAL_SERVER_ERROR,
                messege: error.messege
            }
            
        }
    }

    validateRequestData(email) {
        if(!email) {
            throw new UserException(HttpStatus.BAD_REQUEST, "User email was not informed");
        }
    }

    validateUserNotFound(user) {
        if (!user) {
            throw new Error(HttpStatus.BAD_REQUEST, "USer was not found");
        }
    }

    validateAuthenticatedUser(user, authUser) {
        if(!authUser || Number(user.id) !== Number(authUser.id)) {
            throw new UserException(HttpStatus.FORBIDDEN, "You cannot see user data");
        }
    }

    async getAccessToken(req) {
        try {
            const { email, password } = req.body;
            this.validateAccessTokenData(email, password);
            let user = await UserRepository.findByEmail(email);
            this.validateUserNotFound(user);
            await this.validatePassword(password, user.password);
            const authUser = {
                id: user.id,
                name: user.name,
                email: user.email
            };
            const accessToken = jwt.sign({ authUser }, Secrets.API_SECRET, {
                expiresIn : "1d"
            });
            return {
                status: HttpStatus.SUCCESS,
                accessToken
            };
        } catch (error) {
            return {
                status: error.status ? error.status : HttpStatus.INTERNAL_SERVER_ERROR,
                messege: error.messege
            }
        }
        
    }

    validateAccessTokenData(email, password) {
        if (!email || !password) {
            throw new UserException(HttpStatus.UNAUTHORIZED, "Email and Password must be informed");
        }
    }

    async validatePassword(password, hasPassword) {
        if ((!await bcrypt.compare(password, hasPassword))) {
            throw new UserException(HttpStatus.UNAUTHORIZED,  "Password doesnt match");
        }
    }
}

export default new UserService();
