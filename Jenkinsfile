pipeline {
    agent any

    environment {
        GRADLEW_PATH = './gradlew'
        BUILD_DIR = 'build'
        REPORT_DIR = "${BUILD_DIR}/test-results/test"
        ARTIFACTS_DIR = "${BUILD_DIR}/libs"
    }

    options {
        skipStagesAfterUnstable()
        timestamps()
    }

    stages {
        stage('Checkout Source') {
            steps {
                echo "[Stage] Checking out source code..."
                checkout scm
            }
        }

        stage('Setup Environment') {
            steps {
                echo "[Stage] Preparing build environment..."
                sh '''
                    echo "[Info] Setting executable permission on gradlew"
                    chmod +x ${GRADLEW_PATH}
                '''
            }
        }

        stage('Build and Run Tests') {
            steps {
                echo "[Stage] Executing build and test tasks..."
                sh '''
                    echo "[Info] Cleaning and building project"
                    ${GRADLEW_PATH} clean build --no-daemon --console=plain
                '''
            }
        }
    }

    post {
        always {
            echo "[Post] Archiving artifacts and test results..."
            junit "${REPORT_DIR}/TEST-*.xml"
            archiveArtifacts artifacts: "${ARTIFACTS_DIR}/*.jar", allowEmptyArchive: true
            archiveArtifacts artifacts: "${REPORT_DIR}/**", allowEmptyArchive: true
        }

        success {
            echo "[Success] ✅ All stages completed successfully."
            emailext body: '빌드 작업이 모두 정상적으로 완료되었습니다.',
                     subject: '빌드 성공',
                     to: 'chosw1002@naver.com'
        }

        unstable {
            echo "[Unstable] ⚠️ Tests passed but some conditions marked build unstable."
        }

        failure {
            echo "[Failure] ❌ Build or tests failed."
        }

        cleanup {
            echo "[Cleanup] 🧹 Cleaning up temporary resources if needed..."
        }
    }
}