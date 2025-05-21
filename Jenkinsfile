pipeline {
	agent any

    environment {
		JUNIT_JAR_URL  = 'https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.7.1/junit-platform-console-standalone-1.7.1.jar'
        JUNIT_JAR_PATH = 'lib\\junit.jar'
        CLASS_DIR      = 'classes'
        REPORT_DIR     = 'test-reports'
    }

    stages {
		stage('Checkout') {
			steps {
				checkout scm
            }
        }

        stage('Prepare') {
			steps {
				bat """
                if not exist lib mkdir lib
                if not exist %CLASS_DIR% mkdir %CLASS_DIR%
                if not exist %REPORT_DIR% mkdir %REPORT_DIR%

                echo [+] Downloading JUnit...
                curl -L -o %JUNIT_JAR_PATH% %JUNIT_JAR_URL%
                """
            }
        }

        stage('Build') {
			steps {
				bat """
                echo [+] Compiling sources...
                cd Test2
                dir /S /B src\\*.java > sources.txt
                javac -encoding UTF-8 -d ..\\%CLASS_DIR% -cp ..\\%JUNIT_JAR_PATH% @sources.txt
                cd ..
                """
            }
        }

        stage('Test') {
			steps {
				bat """
                echo [+] Running tests...
                java -jar %JUNIT_JAR_PATH% ^
                     --class-path %CLASS_DIR% ^
                     --scan-class-path ^
                     --reports-dir %REPORT_DIR% ^
                     --details=tree ^
                     --details-theme=ascii
                """
            }
        }
    }

    post {
		always {
			echo "[] Archiving test results..."
            junit  'test-reports/*.xml'
            archiveArtifacts artifacts: 'test-reports/**/*', allowEmptyArchive: true
        }
        failure {
			echo 'Build or test failed!'
        }
        success {
			echo 'Build and test succeeded!'
        }
    }
}
