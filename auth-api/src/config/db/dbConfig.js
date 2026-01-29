import Sequelize  from "sequelize";

const sequelize = new Sequelize("auth-db", "postgres", "1y5h6j", {
    host : "localhost",
    dialect: "postgres",
    quoteIdentifiers: false,
    define: {
        syncOnAssociation: true,
        timestamps: false,
        underscored: true,
        underscoredAll: true,
        freezeTableName: true
    }
});

sequelize.authentication()
.then(() => {
    console.info("Connection has been stablished!")
}).catch((err) => {
    console.error("Unable to connect to the database");
    console.error(err.messege);
});



export default sequelize;