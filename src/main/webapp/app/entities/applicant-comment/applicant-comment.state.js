(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .config(stateConfig);

  stateConfig.$inject = ['$stateProvider'];

  function stateConfig($stateProvider) {
    $stateProvider
      .state('applicant-comment', {
        parent: 'entity',
        url: '/applicant-comment',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'ApplicantComments'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/applicant-comment/applicant-comments.html',
            controller: 'ApplicantCommentController',
            controllerAs: 'vm'
          }
        },
        resolve: {}
      })
      .state('applicant-comment-detail', {
        parent: 'applicant-comment',
        url: '/applicant-comment/{id}',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'ApplicantComment'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/applicant-comment/applicant-comment-detail.html',
            controller: 'ApplicantCommentDetailController',
            controllerAs: 'vm'
          }
        },
        resolve: {
          entity: ['$stateParams', 'ApplicantComment', function ($stateParams, ApplicantComment) {
            return ApplicantComment.get({id: $stateParams.id}).$promise;
          }],
          previousState: ["$state", function ($state) {
            var currentStateData = {
              name: $state.current.name || 'applicant-comment',
              params: $state.params,
              url: $state.href($state.current.name, $state.params)
            };
            return currentStateData;
          }]
        }
      })
      .state('applicant-comment-detail.edit', {
        parent: 'applicant-comment-detail',
        url: '/detail/edit',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/applicant-comment/applicant-comment-dialog.html',
            controller: 'ApplicantCommentDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['ApplicantComment', function (ApplicantComment) {
                return ApplicantComment.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('^', {}, {reload: false});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('applicant-comment.new', {
        parent: 'applicant-comment',
        url: '/new',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/applicant-comment/applicant-comment-dialog.html',
            controller: 'ApplicantCommentDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: function () {
                return {
                  name: null,
                  description: null,
                  id: null
                };
              }
            }
          }).result.then(function () {
            $state.go('applicant-comment', null, {reload: 'applicant-comment'});
          }, function () {
            $state.go('applicant-comment');
          });
        }]
      })
      .state('applicant-comment.edit', {
        parent: 'applicant-comment',
        url: '/{id}/edit',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/applicant-comment/applicant-comment-dialog.html',
            controller: 'ApplicantCommentDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['ApplicantComment', function (ApplicantComment) {
                return ApplicantComment.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('applicant-comment', null, {reload: 'applicant-comment'});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('applicant-comment.delete', {
        parent: 'applicant-comment',
        url: '/{id}/delete',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/applicant-comment/applicant-comment-delete-dialog.html',
            controller: 'ApplicantCommentDeleteController',
            controllerAs: 'vm',
            size: 'md',
            resolve: {
              entity: ['ApplicantComment', function (ApplicantComment) {
                return ApplicantComment.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('applicant-comment', null, {reload: 'applicant-comment'});
          }, function () {
            $state.go('^');
          });
        }]
      });
  }

})();
