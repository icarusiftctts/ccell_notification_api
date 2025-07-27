    package com.example;

    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;

    import java.util.TimeZone;
    import jakarta.annotation.PostConstruct;
    import org.springframework.scheduling.annotation.EnableScheduling;

    @EnableScheduling
    @SpringBootApplication
    public class NotificationPanelBackendApplication {
        public static void main(String[] args) {
            System.out.println("🚀 Starting Spring Boot Application...");
            try {
                // modified by cursor - added comprehensive startup logging for debugging
                System.out.println("📊 Active Profile: " + System.getenv("SPRING_PROFILES_ACTIVE"));
                System.out.println("🌐 Port: " + System.getenv("PORT"));
                System.out.println("️ Database URL: " + (System.getenv("SPRING_DATASOURCE_URL") != null ? "Set" : "Not Set"));
                System.out.println(" Firebase Config: " + (System.getenv("FIREBASE_CONFIG_BASE64") != null ? "Set" : "Not Set"));
                System.out.println(" Database Username: " + (System.getenv("SPRING_DATASOURCE_USERNAME") != null ? "Set" : "Not Set"));
                System.out.println(" Database Password: " + (System.getenv("SPRING_DATASOURCE_PASSWORD") != null ? "Set" : "Not Set"));
                
                // modified by cursor - validate required environment variables
                validateEnvironmentVariables();
                
                SpringApplication.run(NotificationPanelBackendApplication.class, args);
                System.out.println("✅ Application started successfully!");
            } catch (Exception e) {
                System.err.println("❌ Application failed to start");
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace();
                System.exit(1);
            }
        }

        // modified by cursor - added environment validation
        private static void validateEnvironmentVariables() {
            String[] requiredVars = {
                "SPRING_DATASOURCE_URL",
                "SPRING_DATASOURCE_USERNAME", 
                "SPRING_DATASOURCE_PASSWORD",
                "FIREBASE_CONFIG_BASE64"
            };
            
            for (String var : requiredVars) {
                if (System.getenv(var) == null || System.getenv(var).trim().isEmpty()) {
                    System.err.println("❌ Required environment variable is missing: " + var);
                    throw new IllegalStateException("Missing required environment variable: " + var);
                }
            }
            System.out.println("✅ All required environment variables are set");
        }

        @PostConstruct
        public void init() {
            // modified by cursor - Setting Spring Boot default timezone to IST
            TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
            System.out.println("⏰ Timezone set to Asia/Kolkata");
        }
    }
