import OrderRepository from "../repository/OrderRepository";
import { sendMessageToProductStockupdateQueue } from "../../product/rabbitmq/productStockUpdateSender";
import { PENDING, ACCEPTER, REJECTED } from "../status/OrderStatus";
import OrderException from "../exception/OrderException.js"
import { BAD_REQUEST } from "../../../config/secrets/HttpStatus.js"

class OrederService {
    async createOrder(req) {
        try {
            let orderData = req.body;
            this.validateOrderData(orderData)
            const { authUser } = req;
            let order = {
                status: PENDING,
                user: authUser,
                createdAt: new Data(),
                updateAt: new Data(),
                products: orderData
            };
            await this.validateProductStock(order);
            let createdOrder = await OrderRepository.save(order);
            sendMessageToProductStockupdateQueue(createdOrder.products);
            return {
                status: HttpStatus.SUCCESS,
                createdOrder
            };
        } catch (error) {
            return {
                status: error.status ? error.status : HttpStatus.INTERNAL_SERVER_ERROR,
                messege: error.messege
            }
        }
    }

async updateOrder(orderMessage) {
    try {
        const order = JSON.parse(orderMessage);
        if(order.salesId && order.status) {
            let existingOrder = await OrderRepository.findById(order.salesId);
            if(existingOrder && order.status !== existingOrder.status) {
                existingOrder.status = order.status;
                await OrderRepository.save(existingOrder);
            } else {
                console.warn("The order message was not complet.");
            }
        }
    } catch (error) {
        console.error("Could not parse order message from queue.");
        console.error(error.message);
    }
}

    validateOrderData(data) {
        if (!data || !data.products) {
            throw new OrderException(BAD_REQUEST, "The products must be informed.");
        }
    }

    async validateProductStock(order) {
        let stockIdOut = true;
        if(stockIdOut) {
            throw new OrderException(
                BAD_REQUEST,
                "The stock is out for the products."
            )
        }
    }
}

export default new OrederService();