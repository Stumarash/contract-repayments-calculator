# 📱 Contract Repayments Calculator

> A modern, responsive web application for calculating device contract repayments with user authentication and mobile-optimized interface.

## 🚀 Features

- **💰 Smart Calculator**: Calculate monthly repayments for 12, 24, and 36-month contracts at 6.5% interest
- **🔐 User Authentication**: Secure registration and login system
- **📱 Mobile-First Design**: Responsive UI that works seamlessly on all devices
- **🎨 Modern Interface**: Glassmorphism design with interactive elements
- **⚡ Real-time Validation**: Client-side form validation with instant feedback
- **🛡️ Security**: Spring Security 6 with modern authentication patterns
- **📊 Interactive Results**: Selectable payment options with visual comparison

## 🛠️ Technology Stack

| Technology | Version | Purpose |
|------------|---------|----------|
| **Java** | 21 | Runtime environment |
| **Spring Boot** | 3.3.1 | Application framework |
| **Spring Security** | 6.x | Authentication & authorization |
| **Thymeleaf** | 3.x | Template engine |
| **H2 Database** | 2.x | In-memory database |
| **Maven** | 3.x | Build tool |
| **JUnit** | 5.x | Testing framework |
| **Lombok** | Latest | Code generation |
| **MapStruct** | 1.5.5.Final | Object mapping |
| **Bootstrap** | 5.3.2 | UI framework |
| **Spring Java Format** | 0.0.42 | Code formatting |

## 🏃‍♂️ Quick Start

### Prerequisites
- **Java 21** or higher
- **Maven 3.6+** (or use included wrapper)
- **Git** for cloning

### 1️⃣ Clone & Setup
```bash
# Clone the repository
git clone https://github.com/Stumarash/contract-repayments-calculator.git
cd contract-repayments-calculator

# Verify Java version
java -version  # Should show Java 21+
```

### 2️⃣ Build & Run
```bash
# Clean build (recommended first time)
mvn clean install

# Start the application
mvn spring-boot:run

# Alternative: Use Maven wrapper (if Maven not installed)
./mvnw clean install
./mvnw spring-boot:run
```

### 3️⃣ Access the Application

🌐 **Web Interface**: http://localhost:8080
- **Calculator**: Modern glassmorphism design with ZAR currency
- **Results**: Interactive payment options with visual comparison
- **Authentication**: Clean registration and login forms

📋 **API Documentation**: http://localhost:8080/swagger-ui.html

🗄️ **H2 Database Console**: http://localhost:8080/h2-console
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: *(leave empty)*

## 📱 User Journey

### First Time Users
1. **Visit**: http://localhost:8080 → Redirects to calculator
2. **Calculate**: Enter device amount (R100 - R100,000) → View repayment options
3. **Register**: Create account to save calculations
4. **Login**: Access personalized features

### Navigation Flow
```
/ → /calculator (Main page)
├── /register → /login (After registration)
├── /login → /calculator (After authentication)
└── /calculate → /results → /calculator
```

## 🧪 Testing

```bash
# Run all tests
mvn test

# Run with coverage report
mvn clean test jacoco:report

# View coverage report
open target/site/jacoco/index.html
```

**Test Coverage**: 100% (20/20 tests passing)

## 🔧 Development

### IDE Setup
1. **Enable Lombok**: Install Lombok plugin in your IDE
2. **Enable Annotation Processing**: Required for Lombok and MapStruct
3. **Java 21**: Configure project SDK

### Code Formatting
```bash
# Apply consistent code formatting
mvn spring-javaformat:apply

# Check code formatting
mvn spring-javaformat:validate
```

### Profiles
- **Default**: Development profile with H2 database
- **Test**: Testing profile with in-memory database

### Key Endpoints

#### Web Pages
- `GET /` → Calculator page
- `GET /login` → Login form
- `GET /register` → Registration form
- `GET /calculate?amount=X` → Results page

#### REST API
- `GET /api/v1/contracts/repayments?amount=X` → JSON calculations
- `POST /api/v1/contracts/register` → API registration

## 🐳 Docker Deployment

```bash
# Build Docker image
docker build -t contract-calculator .

# Run container
docker run -p 8080:8080 contract-calculator
```

## ☸️ Kubernetes Deployment

### Prerequisites
- Kubernetes cluster
- Helm 3.x installed
- kubectl configured

### Deploy
```bash
# Install with Helm
helm install calculator ./helm-charts

# Port forward for local access
kubectl port-forward svc/calculator 8080:8080

# Custom configuration
helm install calculator ./helm-charts -f custom-values.yaml
```

## 📊 Application Architecture

```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│   Web Browser   │◄──►│  Spring Boot App │◄──►│   H2 Database   │
│  (Thymeleaf)    │    │   (Port 8080)    │    │  (In-Memory)    │
└─────────────────┘    └──────────────────┘    └─────────────────┘
         │                       │
         │              ┌────────▼────────┐
         └──────────────►│   REST API      │
                         │ (/api/v1/...)   │
                         └─────────────────┘
```

## 🎨 Live Application Showcase

### 🏠 Calculator Page (`/calculator`)
- **Modern glassmorphism design** with gradient backgrounds
- **South African Rand (R) currency** display throughout
- **Real-time validation** for amounts between R100 - R100,000
- **Mobile-responsive** input fields with touch-friendly buttons
- **Interactive navigation** to registration and login

### 📊 Results Page (`/calculate?amount=X`)
- **Visual payment comparison** for 12, 24, and 36-month terms
- **Interactive selection** with hover effects and animations
- **Clear pricing display** with monthly and total amounts in ZAR
- **Smart recommendations** (Most Popular, Best Value badges)
- **Responsive card layout** optimized for all screen sizes

### 🔐 Authentication Pages
- **Registration** (`/register`): Clean form with password visibility toggle
- **Login** (`/login`): Streamlined authentication with error handling
- **Consistent styling** across all forms with Bootstrap 5.3.2
- **Form validation** with real-time feedback and error messages

### 🎨 UI Features

- **🌈 Modern Design**: Gradient backgrounds with glassmorphism effects
- **📱 Responsive Layout**: Mobile-first design with Bootstrap 5.3.2
- **⚡ Interactive Elements**: Hover effects, transitions, and animations
- **✅ Form Validation**: Real-time validation with visual feedback
- **🔒 Security**: Password visibility toggle and secure authentication
- **🎯 User Experience**: Intuitive navigation and clear call-to-actions
- **💰 Currency**: Proper South African Rand (R) formatting throughout

## 🔍 Troubleshooting

### Common Issues

**Port 8080 already in use**
```bash
# Use different port
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8081
```

**Lombok not working**
```bash
# Reinstall dependencies
mvn clean install -U
```

**Tests failing**
```bash
# Skip tests during build
mvn clean install -DskipTests
```

### Logs
```bash
# View application logs
tail -f logs/application.log

# Enable debug logging
mvn spring-boot:run -Dspring-boot.run.arguments=--logging.level.com.contact.calculator=DEBUG
```

## 📚 Documentation

- **[API Documentation](src/main/resources/api/api.yaml)**: OpenAPI specification
- **[Use Case Diagram](src/main/resources/contract-repayments-usecase.png)**: System design
- **[Test Coverage](target/site/jacoco/index.html)**: Coverage reports
- **[Navigation Guide](NAVIGATION_ALIGNMENT.md)**: Controller-template alignment
- **[UI Modernization](TEMPLATE_MODERNIZATION.md)**: Design improvements
- **[Final Validation](FINAL_VALIDATION_SUMMARY.md)**: Complete project validation results

## 🤝 Contributing

1. Fork the repository
2. Create feature branch: `git checkout -b feature/branch-name`
3. Commit changes: `git commit -m 'commit message'`
4. Push to branch: `git push origin feature/branch-name`
5. Open Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

**🎯 Ready to calculate? Start with:** `mvn spring-boot:run` **and visit** http://localhost:8080
