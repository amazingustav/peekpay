help: ## Show this help
	@fgrep -h "##" $(MAKEFILE_LIST) | fgrep -v fgrep | sed -E 's/:.*##/:\t/g'

up: ## Start the application
	make build-app
	make run-app

build-app: ## Build application
	@./gradlew clean build

run-app: ## Run application
	@./gradlew bootRun

test-app: ## Execute application tests
	@./gradlew clean test
