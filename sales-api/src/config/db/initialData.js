import Order from "../../modules/sales/model/Order.js";

export async function createInitialData() {
    await Order.collection.drop();
    await Order.create({
        products: [
            {
                productId: 1001,
                quantity: 2
            },
            {
                productId: 1002,
                quantity: 1
            },
            {
                productId: 1003,
                quantity: 1
            }
        ],
        user: {
            id: "234j5h6gf65y4u3",
            name: "User Test",
            email: "userteste@gmail.com"
        },
        status: "APPROVED",
        createdAt: new Date(),
        updateAt: new Date()
    });
    await Order.create({
        products: [
            {
                productId: 1001,
                quantity: 4
            },
            {
                productId: 1002,
                quantity: 2
            }
        ],
        user: {
            id: "234j5hwdfwfwfwfw5y4u3",
            name: "User Test 2",
            email: "userteste2@gmail.com"
        },
        status: "REJECTED",
        createdAt: new Date(),
        updateAt: new Date()
    });

    let initialData = await Order.find()
        console.info(`Initial data was create: ${JSON.stringify(initialData)}`);
    
}