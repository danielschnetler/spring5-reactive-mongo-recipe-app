# Update the VARIANT arg in devcontainer.json to pick a Java version >= 11
ARG VARIANT=11
FROM openjdk:${VARIANT}-jdk-buster

# Options for setup script
ARG INSTALL_ZSH="true"
ARG UPGRADE_PACKAGES="false"
ARG USERNAME=vscode
ARG USER_UID=1000
ARG USER_GID=$USER_UID

# Install needed packages and setup non-root user. Use a separate RUN statement to add your own dependencies.
COPY library-scripts/*.sh /tmp/library-scripts/
RUN /bin/bash /tmp/library-scripts/common-debian.sh "${INSTALL_ZSH}" "${USERNAME}" "${USER_UID}" "${USER_GID}" "${UPGRADE_PACKAGES}" \
    && if [ ! -d "/docker-java-home" ]; then ln -s "${JAVA_HOME}" /docker-java-home; fi \
    && apt-get clean -y && rm -rf /var/lib/apt/lists/* /tmp/library-scripts/common-debian.sh

# [Optional] Install Gradle
ARG INSTALL_GRADLE="true"
ARG GRADLE_VERSION=5.4.1
ARG GRADLE_DOWNLOAD_SHA="no-check"
ENV GRADLE_HOME=/usr/local/share/gradle
RUN if [ "${INSTALL_GRADLE}" = "true" ]; then /bin/bash /tmp/library-scripts/gradle-debian.sh ${GRADLE_VERSION} ${GRADLE_HOME} ${USERNAME} ${GRADLE_DOWNLOAD_SHA}; fi \
    && rm -f /tmp/library-scripts/gradle-debian.sh



# [Optional] Uncomment this section to install additional OS packages.
# RUN apt-get update && export DEBIAN_FRONTEND=noninteractive \
#     && apt-get -y install --no-install-recommends <your-package-list-here>