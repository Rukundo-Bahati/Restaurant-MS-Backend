-- Create Users Table
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(255),
                       email VARCHAR(255) UNIQUE,
                       password VARCHAR(255),
                       role VARCHAR(50),
                       verified BOOLEAN
);

-- Create Restaurants Table
CREATE TABLE restaurant (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(255),
                            location VARCHAR(255)
);

-- Create Menu Table
CREATE TABLE menu (
                      id SERIAL PRIMARY KEY,
                      item_name VARCHAR(255),
                      price DOUBLE PRECISION,
                      stock INTEGER,
                      restaurant_id INTEGER REFERENCES restaurant(id)
);

-- Create Orders Table
CREATE TABLE orders (
                        id SERIAL PRIMARY KEY,
                        user_id INTEGER REFERENCES users(id),
                        menu_id INTEGER REFERENCES menu(id),
                        quantity INTEGER,
                        status VARCHAR(50),
                        order_time TIMESTAMP
);

-- Trigger to update menu stock on order
CREATE OR REPLACE FUNCTION update_menu_stock() RETURNS TRIGGER AS $$
BEGIN
UPDATE menu SET stock = stock - NEW.quantity WHERE id = NEW.menu_id;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER order_trigger
    AFTER INSERT ON orders
    FOR EACH ROW
    EXECUTE FUNCTION update_menu_stock();
