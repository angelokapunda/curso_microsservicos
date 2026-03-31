import mongoose, { Schema } from "mongoose";

const schema = mongoose.Schema;
const model =  mongoose.model;

const OrderSchema = new Schema({
    products: {
        type : Array,
        require: true
    },
    user: {
        type : Object,
        require: true
    },
    status: {
        type : String,
        require: true
    },
    createdAt: {
        type : Date,
        require: true
    },
    updateAt: {
        type : Date,
        require: true
    }
});

export default model("Order", OrderSchema);