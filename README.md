Quiz API
=============================
![quiz-api](https://user-images.githubusercontent.com/32781877/158852533-29305164-9e9e-41b2-a808-fb1d717b70cf.png)

### Endpoint Documentation

- [ ] `GET quiz`
    - Returns the collection of `Quiz` elements

- [ ] `POST quiz`
    Creates a quiz and adds to collection
    - Returns the `Quiz` that it created

- [ ] `DELETE quiz/{id}`
    Deletes the specified quiz from collection
    - Returns the deleted `Quiz`

- [ ] `PATCH quiz/{id}/rename/{newName}`
    Rename the specified quiz using the new name given
    - Returns the renamed `Quiz`

- [ ] `GET quiz/{id}/random`
    - Returns a random `Question` from the specified quiz

- [ ] `PATCH quiz/{id}/add`
    Adds a question to the specified quiz
    - Receives a `Question`
    - Returns the modified `Quiz`
    
- [ ] `DELETE quiz/{id}/delete/{questionID}`
    Deletes the specified question from the specified quiz
    - Returns the deleted `Question`
