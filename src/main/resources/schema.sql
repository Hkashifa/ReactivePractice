CREATE TABLE IF NOT EXISTS public.orders (
                                      id SERIAL PRIMARY KEY,
                                      order_status VARCHAR(50) NOT NULL,
                                      total_price INT NOT NULL
);

CREATE TABLE IF NOT EXISTS public.food_items(
                                    id SERIAL PRIMARY KEY,
                                    name VARCHAR(50) NOT NULL,
                                    description VARCHAR(255) NOT NULL,
                                    price INT NOT NULL
);

CREATE TABLE IF NOT EXISTS public.order_food_items(
                                    id SERIAL PRIMARY KEY,
                                    order_id INTEGER,
                                    food_item_id INTEGER,
                                    CONSTRAINT fk_users FOREIGN KEY(order_id) REFERENCES orders(id),
                                    CONSTRAINT fk_roles FOREIGN KEY(food_item_id ) REFERENCES food_items(id)
);