Vraptor Simple Validator 
========================

##How to use

### 1 - Inject the `SimpleValidator` at your controller:

```
@Resource
public class DogController{
	
	private SimpleValidator validator;

	public DogController(SimpleValidator validator){		
		this.validator = validator
	}
}

```

### 2 - Call `validator.validate` on the desired action. The first argument is the object that you want to validate and
the second is the `ValidationStrategy`. 

By the way, the `vraptor-simple-validator` provides a diversity of defaults validations that you may want to use. 

You can access them through static methods of the class `ValidationStrategies`
Curretly, the following validations are implemented:

1. matches("someString") - Check if the `String`s are equals to each other
2. notEmpty() - Check if the given `List` is not empty
3. notEmptyNorNull() - Check if the given `String` is not empty/null
4. notNull() - Check if the given `Object` is not null
5. lessThan(12l) - Check if the given long is less than 12
6. biggerThan(12l) - Check if the given long is bigger than 12

When using them you will need to tell which `message.properties` key you want to use on error.
The validation call will as simple as that:

```
	validator.validate(dog.getName(), ValidationStrategies.maches("Bob").key("properties.key"));
```

You can create a custom `ValidationStrategy` in a very simple way, this will be covered later.

### 3 - Specify where to go when the validation fail. This is just like you would do with the default vraptor `Validator`:

```
	validator.validate(dog.getName(), maches("Bob").key("properties.key"))
			 .onErrorRedirectTo(this).createDog();
```

### 4 - Optional: Tell the validator to add a confirmation message if everything goes fine.

```
	validator.validate(dog.getName(), maches("Bob").key("name.should.be.bob"))
			 .onSuccessAddConfirmationMessage("confirmation.key")
			 .onErrorRedirectTo(this).createDog();

```

### 5 - Display errors or confirmation in view.
The list of errors will be automatically inserted on the request with the name `errors`, you will only need to iterate that list:

```
<c:if test="${not empty errors}">
	<ul class="error-messages">
		<c:forEach var="error" items="${errors}">
			<li class="${error.category}">${error.message}</li>
		</c:forEach>
	</ul>
</c:if>
``` 

The confirmation will be inserted with the name `confirmations`:

```
<c:if test="${not empty confirmations}">
	<ul class="confirmation-messages">
		<c:forEach var="confirmation" items="${confirmations}">
			<li class="${confirmation.category}">${confirmation.message}</li>
		</c:forEach>
	</ul>
</c:if>
```
Tip: of course, the structure is exactly the same, so you can create a taglib to avoid copy/paste.

##Creating a custom `ValidationStrategy`

To create a `ValidationStrategy`, only need to create a class annotated with `@Component` that extends `ValidationStrategy` and implement the method `addErrors` as you desire:

```
@Component
public class DogValidator extends ValidationStrategy<Dog>{
	@Override
	public void addErrors(Dog dog) {
		if(dog == null) addError("invalid.dog");
		if(dog != null && dog.getNumberOfPaws() < 4)
			addAlert("dog.has.less.than.4.paws", dog.getNumberOfPaws());
	}	
}
```
Tip: Yep, it is a vraptor component, so you can inject whatever you want at its constructor.

To use the custom strategy, just pass its class to the validator:
```
validator.validate(dog, DogValidator.class)
		 .onSuccessAddConfirmationMessage("confirmation.key")
		 .onErrorRedirectTo(this).createDog();
```

##What happens when a validation fails?

### 1 - The program flow will be interrupted.

What does it mean? Well, you don't need to place an if verifying if the validation was ok or something like that.
You can just call the validator:

```
public void newDog(Dog dog){

	validator.validate(dog, DogValidator.class)
			 .onSuccessAddConfirmationMessage("confirmation.key")
			 .onErrorRedirectTo(this).createDog();
	
	dogs.save(dog); // This line won't be executed if the validation fails
}
```
### 2 - The db transaction will rollback

If you use `vraptor-hibernate` or `vraptor-jpa` to controll the transaction for you, it will rollback if there are validation errors

### 3 - The errors will be included at your jsp

All the validation errors/alerts will be automatically included at the list `${errors}` at your jsp.



