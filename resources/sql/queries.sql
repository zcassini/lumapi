-- :name create-user! :! :n
-- :doc creates a new user record
INSERT INTO users
(id, first_name, last_name, email, pass)
VALUES (:id, :first_name, :last_name, :email, :pass)

-- :name update-user! :! :n
-- :doc update an existing user record
UPDATE users
SET first_name = :first_name, last_name = :last_name, email = :email
WHERE id = :id

-- :name get-user :? :1
-- :doc retrieve a user given the id
SELECT * FROM users
WHERE id = :id

-- :name delete-user! :! :n
-- :doc delete a user given the id
DELETE FROM users
WHERE id = :id

-- :name get-url :? :1
-- :doc retrieve a url given the id
SELECT * FROM urls
WHERE id = :id

-- :name add-url! :! :n
-- :doc creates a new url record
INSERT INTO urls
(url, timestamp)
VALUES (:url, :timestamp)

-- :name get-urls :? :*
-- :doc retrieve all available urls
SELECT * FROM urls
